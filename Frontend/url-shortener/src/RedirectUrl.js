import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Url-shorts.css';

function RedirectUrl({ setUpdateUrls }) {
    const [shortUrl, setShortUrl] = useState('');
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);
    const [loading, setLoading] = useState(false);

    // Function to handle form submission and redirection
    const redirectFunc = (e) => {
        e.preventDefault();

        // Validate the form before proceeding
        const errors = validate(shortUrl);
        setFormErrors(errors);

        // Proceed only if no validation errors
        if (Object.keys(errors).length === 0) {
            setLoading(true); // Set loading state
            const URL = `http://localhost:8080/updateurl/${shortUrl}`;
            axios.put(URL)
                .then((response) => {
                    window.open(response.data, '_blank').focus(); // Open the redirected URL in a new tab
                    setUpdateUrls(true); // Trigger URL list update
                    setIsSubmit(true);
                    setLoading(false); // Stop loading
                })
                .catch(error => {
                    setLoading(false); // Stop loading on error
                    if (error.response && error.response.status === 404) {
                        window.alert(error.response.data); // Handle URL not found
                    } else {
                        console.error(error.message); // Log general errors
                    }
                });
        } else {
            setIsSubmit(true); // Mark form as submitted, so errors are displayed
        }
    };

    // Validation function for short URL key
    const validate = (value) => {
        const errors = {};
        const regex = /^urls-[a-z0-9]{8}$/; // Assuming this format for short keys
        if (!value) {
            errors.shortUrl = 'Short key is required!';
        }
        else if (!regex.test(value)) {
            errors.shortUrl = 'Please provide a valid short key!';
        }
        return errors;
    };

    useEffect(() => {
        console.log(formErrors);
        if (Object.keys(formErrors).length === 0 && isSubmit) {
          console.log(shortUrl);
        }
    }, [formErrors, isSubmit, shortUrl]);        
    
    return (
        <div className="redirect-url-container">
            <form className="redirect-url-form" onSubmit={redirectFunc}>
                <input
                    name="shortUrl"
                    type="text"
                    placeholder="Enter Short Key"
                    className="redirect-url-input"
                    value={shortUrl}
                    onChange={e => setShortUrl(e.target.value)}
                    required
                />
                {formErrors.shortUrl && <p className="error-text">{formErrors.shortUrl}</p>}
                <button className="redirect-url-button" type="submit" disabled={loading}>
                    {loading ? 'Redirecting...' : 'Redirect'}
                </button>
            </form>
        </div>
    );
}

export default RedirectUrl;
