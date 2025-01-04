import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ShortenUrl from './ShortenUrl';
import axios from 'axios';

jest.mock('axios');

describe('ShortenUrl component', () => {
    test('renders the shorten URL form', () => {
        render(<ShortenUrl setUpdateUrls={jest.fn()} />);
        expect(screen.getByPlaceholderText('Enter Long URL')).toBeInTheDocument();
        expect(screen.getByText('Shorten URL')).toBeInTheDocument();
    });

    test('validates and submits the URL form', async () => {
        axios.post.mockResolvedValueOnce({ data: { shortKey: 'urls-12345678' } });

        render(<ShortenUrl setUpdateUrls={jest.fn()} />);

        fireEvent.change(screen.getByPlaceholderText('Enter Long URL'), { target: { value: 'http://example.com' } });
        fireEvent.click(screen.getByText('Shorten URL'));

        await waitFor(() => {
            expect(screen.getByText('Shortened URL: urls-12345678')).toBeInTheDocument();
        });
    });

    test('shows error message for invalid URL', async () => {
        render(<ShortenUrl setUpdateUrls={jest.fn()} />);
        fireEvent.click(screen.getByText('Shorten URL'));

        await waitFor(() => {
            expect(screen.getByText('Long URL is required!')).toBeInTheDocument();
        });
    });
});
