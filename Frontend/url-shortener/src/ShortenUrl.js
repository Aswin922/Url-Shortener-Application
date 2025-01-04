import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Url-shorts.css';

function ShortenUrl({ setUpdateUrls }) {
    // Initial state for long and short URLs
    const initialUrlState = {
        longUrl: '',
        shortKey: '',
        clickCount: 0,
    };

    const [originalUrl, setOriginalUrl] = useState(initialUrlState);
    const [shortenUrl, setShortenUrl] = useState(initialUrlState);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

    // Function to handle URL shortening form submission
    const shortenFunc = (e) => {
        e.preventDefault();
        const errors = validate(originalUrl);
        setFormErrors(errors);

        // Proceed if no validation errors
        if (Object.keys(errors).length === 0) {
            const URL = 'http://localhost:8080/addurl';
            axios.post(URL, originalUrl)
                .then((response) => {
                    window.alert('URL shortened successfully!');
                    setShortenUrl(response.data);
                    setOriginalUrl(initialUrlState);
                    setIsSubmit(true);
                    setUpdateUrls(true);  // Trigger re-fetch for URL list
                })
                .catch(error => {
                    if (error.response && error.response.status === 409) {
                        window.alert(error.response.data);  // Handle duplicate URL case
                    } else {
                        console.error(error.message);
                    }
                });
        } else {
            setIsSubmit(true); // Mark form as submitted to display errors
        }
    };

    // Function to validate the URL input
    const validate = (values) => {
        const errors = {};
        const inputElement = document.getElementById("longUrl");

        // URL validation
        if (!values.longUrl) {
            errors.longUrl = 'Long URL is required!';
        } else if (!inputElement.checkValidity()) {
            errors.longUrl = 'Please provide a valid long URL!';
        }

        return errors;
    };

    useEffect(() => {
        console.log(formErrors);
        if (Object.keys(formErrors).length === 0 && isSubmit) {
          console.log(originalUrl);
        }
    }, [formErrors, isSubmit, originalUrl]);   

    return (
        <div className="shorten-url-container">
            <form className="shorten-url-form" onSubmit={shortenFunc}>
                <input
                    id="longUrl"
                    name="longUrl"
                    type="url"
                    placeholder="Enter Long URL"
                    className="shorten-url-input"
                    value={originalUrl.longUrl}
                    onChange={e => setOriginalUrl({ ...originalUrl, longUrl: e.target.value })}
                    required
                />
                {formErrors.longUrl && <p className="error-text">{formErrors.longUrl}</p>}
                <button className="shorten-url-button" type="submit">
                    Shorten URL
                </button>
            </form>

            {/* Display the shortened URL */}
            {shortenUrl.shortKey && (
                <div className="shortened-url-result">
                    <div className="success-message">
                        Shortened URL: {shortenUrl.shortKey}
                    </div>
                </div>
            )}
        </div>
    );
}

export default ShortenUrl;
