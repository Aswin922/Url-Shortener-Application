import { render, screen } from '@testing-library/react';
import App from './App';
import ShortenUrl from './ShortenUrl.js';
import RedirectUrl from './RedirectUrl.js';
import DisplayUrls from './DisplayUrls.js';
import '@testing-library/jest-dom';

// Mock child components
jest.mock('./ShortenUrl', () => jest.fn(() => <div>Shorten a URL</div>));
jest.mock('./RedirectUrl', () => jest.fn(() => <div>RedirectUrl Component</div>));
jest.mock('./DisplayUrls', () => jest.fn(() => <div>DisplayUrls Component</div>));

describe('App component', () => {
    test('renders the App component with all sections', () => {
        render(<App />);

        // Check if the main heading is rendered
        expect(screen.getByText('URL Shortener')).toBeInTheDocument();

        

        // Check for section headings
        expect(screen.getByText('Shorten a URL')).toBeInTheDocument();
        expect(screen.getByText('Redirect using Short Key')).toBeInTheDocument();
        expect(screen.getByText('All URLs')).toBeInTheDocument();
    });

    test('passes setUpdateUrls to ShortenUrl and RedirectUrl components', () => {
        render(<App />);

        // Verify that mocked ShortenUrl and RedirectUrl components were called
        expect(ShortenUrl).toHaveBeenCalledWith(
            expect.objectContaining({
                setUpdateUrls: expect.any(Function),
            }),
            {}
        );

        expect(RedirectUrl).toHaveBeenCalledWith(
            expect.objectContaining({
                setUpdateUrls: expect.any(Function),
            }),
            {}
        );
    });

    test('passes updateUrls and setUpdateUrls to DisplayUrls component', () => {
        render(<App />);

        // Verify that mocked DisplayUrls component was called
        expect(DisplayUrls).toHaveBeenCalledWith(
            expect.objectContaining({
                updateUrls: expect.any(Boolean),
                setUpdateUrls: expect.any(Function),
            }),
            {}
        );
    });
});
