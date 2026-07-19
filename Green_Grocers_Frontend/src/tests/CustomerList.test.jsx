import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import CustomerList from '../component/admin/CustomerList';
import { getAllCustomers } from '../service/AdminService';

// Mock the getAllCustomers service
vi.mock('../service/AdminService', () => ({
  getAllCustomers: vi.fn(),
}));

describe('CustomerList', () => {

  it('handles API errors gracefully', async () => {
    getAllCustomers.mockRejectedValue(new Error('Failed to fetch customers'));

    render(
      <Router>
        <CustomerList />
      </Router>
    );

    // Wait for the component to handle the error
    await waitFor(() => {
      expect(screen.getByText(/Error: Failed to fetch customers/i)).toBeInTheDocument();
    });

    // Ensure no customer data is shown
    expect(screen.queryByText('John Doe')).not.toBeInTheDocument();
    expect(screen.queryByText('Jane Doe')).not.toBeInTheDocument();
  });

  it('renders loading state correctly', async () => {
    getAllCustomers.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(
      <Router>
        <CustomerList />
      </Router>
    );

    // Check if loading indicator is shown
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
  });

  it('renders empty state when no customers are returned', async () => {
    getAllCustomers.mockResolvedValue({ data: [] });

    render(
      <Router>
        <CustomerList />
      </Router>
    );

    // Check if empty state message is shown
    await waitFor(() => {
      expect(screen.getByText(/No customers found/i)).toBeInTheDocument();
    });
  });

  
});
