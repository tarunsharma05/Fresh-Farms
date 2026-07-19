import React from 'react';
import { render, screen } from '@testing-library/react';
import About from '../component/item/About';

describe('About', () => {
  it('renders about section with correct content', () => {
    render(<About />);
    const aboutHeading = screen.getByText(/About Us/i); // Using regular expression for case-insensitive matching
    const aboutText = screen.getByText(/Founded in 2024, Green Grocer has come a long way from its beginnings/i);
    expect(aboutHeading).toBeInTheDocument();
    expect(aboutText).toBeInTheDocument();
  });
});
