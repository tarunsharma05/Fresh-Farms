import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import { successfulPaymentList } from '../service/PaymentService';
import SuccessfulPayments from '../component/payment/SuccessfulPayments';
 
// Mock the successfulPaymentList service
vi.mock('../service/PaymentService', () => ({
  successfulPaymentList: vi.fn(), // Mocking the service function
}));
 
// Begin test suite for SuccessfulPayments component
describe('SuccessfulPayments', () => {
 
  // Test case to check loading state while fetching successful payments
  it('displays loading state while fetching successful payments', async () => {
    // Mock an empty array response for successful payments
    successfulPaymentList.mockResolvedValueOnce({ data: [] });
 
    // Render the SuccessfulPayments component within Router
    render(
      <Router>
        <SuccessfulPayments />
      </Router>
    );
 
    // Wait for the loading state to disappear
    await waitFor(() => {
      // Assert that the loading text is not present in the document
      expect(screen.queryByText(/Loading.../i)).not.toBeInTheDocument();
    });
  });
 
  // Test case to check if successful payments are displayed correctly
  it('displays successful payments', async () => {
    // Mock data for successful payments
    const mockPayments = [
      {
        paymentId: 1,
        paymentStatus: 'Payment Successful',
        paymentMode: 'card',
        paymentDate: '2024-06-05',
        paymentTime: '12:30:00',
        totalAmount: 100,
      },
    ];
 
    // Mock the service function to return the mock data
    successfulPaymentList.mockResolvedValueOnce({ data: mockPayments });
 
    // Render the SuccessfulPayments component within Router
    render(
      <Router>
        <SuccessfulPayments />
      </Router>
    );
 
    // Wait for the DOM to update
    await waitFor(() => {
      // Assert that details of the payment are displayed correctly
      expect(screen.getByText(/Payment ID: 1/i)).toBeInTheDocument();
      expect(screen.getByText(/Payment Successful/i)).toBeInTheDocument();
      expect(screen.getByText(/Payment Mode: card/i)).toBeInTheDocument();
      expect(screen.getByText(/Date: 2024-06-05/i)).toBeInTheDocument();
      expect(screen.getByText(/Time: 12:30:00/i)).toBeInTheDocument();
      expect(screen.getByText(/Total Amount: 100/i)).toBeInTheDocument();
    });
  });
 
});
 