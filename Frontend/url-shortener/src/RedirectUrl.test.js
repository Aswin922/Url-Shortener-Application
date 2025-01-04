import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import RedirectUrl from './RedirectUrl';
import axios from 'axios';

jest.mock('axios');

describe('RedirectUrl component', () => {
    test('renders the redirect URL form', () => {
        render(<RedirectUrl setUpdateUrls={jest.fn()} />);
        expect(screen.getByPlaceholderText('Enter Short Key')).toBeInTheDocument();
        expect(screen.getByText('Redirect')).toBeInTheDocument();
    });

    test('validates and submits the redirect form', async () => {
        axios.put.mockResolvedValueOnce({ data: 'http://redirected.com' });

        render(<RedirectUrl setUpdateUrls={jest.fn()} />);

        fireEvent.change(screen.getByPlaceholderText('Enter Short Key'), { target: { value: 'urls-12345678' } });
        fireEvent.click(screen.getByText('Redirect'));

        await waitFor(() => {
            expect(screen.getByText('Redirecting...')).toBeInTheDocument();
        });
    });

    test('shows error message for invalid short key', async () => {
        render(<RedirectUrl setUpdateUrls={jest.fn()} />);
        fireEvent.click(screen.getByText('Redirect'));

        await waitFor(() => {
            expect(screen.getByText('Short key is required!')).toBeInTheDocument();
        });
    });
});
