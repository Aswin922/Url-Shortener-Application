import '@testing-library/jest-dom';
import { configure } from '@testing-library/react';

// Optionally configure the testing library with custom settings
configure({
    testIdAttribute: 'data-testid', // Customize the test ID attribute if necessary
});
