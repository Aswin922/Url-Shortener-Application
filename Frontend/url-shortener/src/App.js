import './App.css';
import ShortenUrl from './ShortenUrl.js';
import RedirectUrl from './RedirectUrl.js';
import DisplayUrls from './DisplayUrls.js';
import { useState } from 'react';

/**
 * Main App component for the URL Shortener application.
 * This component handles the overall layout and state management
 * for URL shortening, redirecting, and displaying all URLs.
 */
function App() {
    // State to trigger re-fetch of URLs in the DisplayUrls component
    const [updateUrls, setUpdateUrls] = useState(false);

    return (
        <div className="App">
            <header className="App-header">
                <h1>URL Shortener</h1>
            </header>
            <main className="App-main">
                <div className="left-pane">
                    {/* Section for URL shortening feature */}
                    <section className="feature-section">
                        <h2>Shorten a URL</h2>
                        <ShortenUrl setUpdateUrls={setUpdateUrls} />
                    </section>
                    {/* Section for redirecting using the short key */}
                    <section className="feature-section">
                        <h2>Redirect using Short Key</h2>
                        <RedirectUrl setUpdateUrls={setUpdateUrls} />
                    </section>
                </div>
                <div className="right-pane">
                    {/* Section to display all URLs */}
                    <section className="urls-section">
                        <h2>All URLs</h2>
                        <DisplayUrls updateUrls={updateUrls} setUpdateUrls={setUpdateUrls} />
                    </section>
                </div>
            </main>
        </div>
    );
}

export default App;
