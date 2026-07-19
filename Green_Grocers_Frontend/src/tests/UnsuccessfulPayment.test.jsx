import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import { unsuccessfulPaymentList } from '../service/PaymentService'; // Import the service function
import UnsuccessfulPayments from '../component/payment/UnsuccessfulPayments'; // Import the component to be tested
 
// Mock the unsuccessfulPaymentList service
vi.mock('../service/PaymentService', () => ({
  unsuccessfulPaymentList: vi.fn(), // Mocking the service function
}));
 
// Begin test suite for UnsuccessfulPayments component
describe('UnsuccessfulPayments', () => {
 
  // Test case to check loading state while fetching unsuccessful payments
  it('displays loading state while fetching unsuccessful payments', async () => {
    // Mock an empty array response for unsuccessful payments
    unsuccessfulPaymentList.mockResolvedValueOnce({ data: [] });
 
    // Render the UnsuccessfulPayments component within Router
    render(
      <Router>
        <UnsuccessfulPayments />
      </Router>
    );
 
    // Wait for the loading state to disappear
    await waitFor(() => {
      // Assert that the loading text is not present in the document
      expect(screen.queryByText(/Loading.../i)).not.toBeInTheDocument();
    });
  });
 
  // Test case to check if unsuccessful payments are displayed correctly
  it('displays unsuccessful payments', async () => {
    // Mock data for unsuccessful payments
    const mockPayments = [
      {
        paymentId: 1,
        paymentStatus: 'Payment Failed',
        paymentMode: 'card',
        paymentDate: '2024-06-05',
        paymentTime: '12:30:00',
        totalAmount: 100,
      },
    ];
 
    // Mock the service function to return the mock data
    unsuccessfulPaymentList.mockResolvedValueOnce({ data: mockPayments });
 
    // Render the UnsuccessfulPayments component within Router
    render(
      <Router>
        <UnsuccessfulPayments />
      </Router>
    );
 
    // Wait for the DOM to update
    await waitFor(() => {
      // Assert that details of the payment are displayed correctly
      expect(screen.getByText(/Payment ID: 1/i)).toBeInTheDocument();
      expect(screen.getByText(/Payment Failed/i)).toBeInTheDocument();
      expect(screen.getByText(/Payment Mode: card/i)).toBeInTheDocument();
      expect(screen.getByText(/Date: 2024-06-05/i)).toBeInTheDocument();
      expect(screen.getByText(/Time: 12:30:00/i)).toBeInTheDocument();
      expect(screen.getByText(/Total Amount: 100/i)).toBeInTheDocument();
    });
  });
 
});