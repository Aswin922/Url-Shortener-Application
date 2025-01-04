import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import DisplayUrls from './DisplayUrls';
import axios from 'axios';

jest.mock('axios');

describe('DisplayUrls component', () => {
    test('renders the table and fetches URLs', async () => {
        axios.get.mockResolvedValueOnce({
            data: [{ longUrl: 'http://example.com', shortKey: 'urls-12345678', clickCount: 5 }],
        });

        render(<DisplayUrls updateUrls={true} setUpdateUrls={jest.fn()} />);

        await waitFor(() => {
            expect(screen.getByText('http://example.com')).toBeInTheDocument();
            expect(screen.getByText('urls-12345678')).toBeInTheDocument();
            expect(screen.getByText('5')).toBeInTheDocument();
        });
    });

    test('displays an error message if fetching URLs fails', async () => {
        axios.get.mockRejectedValueOnce(new Error('Network Error'));

        render(<DisplayUrls updateUrls={true} setUpdateUrls={jest.fn()} />);

        await waitFor(() => {
            expect(screen.getByText('Failed to load URLs. Please try again later.')).toBeInTheDocument();
        });
    });
});
