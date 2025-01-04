import { useEffect, useState } from 'react';
import axios from 'axios';
import './DisplayUrls.css';

function DisplayUrls({ updateUrls, setUpdateUrls }) {
    const [urls, setUrls] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [deleteSuccess, setDeleteSuccess] = useState('');

    useEffect(() => {
        const fetchUrls = async () => {
            try {
                setLoading(true); // Show loading spinner
                const response = await axios.get('http://localhost:8080/viewallurl');
                setUrls(response.data);
                console.log(response.data);
            } catch (error) {
                console.log(error.message);
                setError('Failed to load URLs. Please try again later.');
            } finally {
                setLoading(false); // Hide loading spinner
            }
        };

        fetchUrls();

        // Reset the update flag after fetching
        setUpdateUrls(false);
    }, [updateUrls, setUpdateUrls]);

    const deleteFunc = async (url) => {
        try {
            await axios.delete(`http://localhost:8080/deleteurl/${url.shortKey}`);
            setUrls((prevUrls) => prevUrls.filter((u) => u.shortKey !== url.shortKey));
            setDeleteSuccess('URL deleted successfully');
            setTimeout(() => setDeleteSuccess(''), 3000); // Clear success message after 3 seconds
        } catch (error) {
            console.log(error.message);
            setError('Failed to delete the URL. Please try again later.');
        }
    };

    return (
        <div className="table-container">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
            
            {loading ? (
                <div className="loading-message">Loading URLs...</div>
            ) : (
                <table className="urls-table">
                    <thead>
                        <tr>
                            <th style={{width:`40%`}}>Long URL</th>
                            <th style={{width:`25%`}}>Short Key</th>
                            <th style={{width:`20%`}}>Click Count</th>
                            <th style={{width:`15%`}}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {error ? (
                            <tr>
                                <td colSpan="4" className="error-message">
                                    {error}
                                </td>
                            </tr>
                        ) : urls.length > 0 ? (
                            urls.map((url) => (
                                <tr key={url.shortKey}>
                                    <td>{url.longUrl}</td>
                                    <td>{url.shortKey}</td>
                                    <td>{url.clickCount}</td>
                                    <td>
                                        <button
                                            className="delete-btn"
                                            onClick={() => deleteFunc(url)}
                                        >
                                            <i className="fa fa-trash-o"></i>
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4" className="no-data">
                                    No URLs found.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}

            {deleteSuccess && <div className="success-message">{deleteSuccess}</div>}
        </div>
    );
}

export default DisplayUrls;
