import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import PaymentList from '../component/payment/PaymentList';
import { paymentList } from '../service/PaymentService';
 
// Mock the paymentList service
vi.mock('../service/PaymentService', () => ({
    paymentList: vi.fn(), // Mocking the service function
}));
 
// Begin test suite for PaymentList component
describe('PaymentList', () => {
 
    // Test case to check loading state while fetching payments
    it('displays loading state while fetching payments', async () => {
        // Mock an empty array response for payments
        paymentList.mockResolvedValueOnce({ data: [] });
   
        // Render the PaymentList component within Router
        render(
            <Router>
                <PaymentList />
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
        paymentList.mockResolvedValueOnce({ data: mockPayments });
   
        // Render the PaymentList component within Router
        render(
            <Router>
                <PaymentList />
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
   
    // Test case to check if failed payments are displayed correctly
    it('displays failed payments', async () => {
        // Mock data for failed payments
        const mockPayments = [
            {
                paymentId: 2,
                paymentStatus: 'Payment Failed',
                paymentMode: 'upi',
                paymentDate: '2024-06-05',
                paymentTime: '14:45:00',
                totalAmount: 200,
            },
        ];
   
        // Mock the service function to return the mock data
        paymentList.mockResolvedValueOnce({ data: mockPayments });
   
        // Render the PaymentList component within Router
        render(
            <Router>
                <PaymentList />
            </Router>
        );
   
        // Wait for the DOM to update
        await waitFor(() => {
            // Assert that details of the payment are displayed correctly
            expect(screen.getByText(/Payment ID: 2/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Failed/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Mode: upi/i)).toBeInTheDocument();
            expect(screen.getByText(/Date: 2024-06-05/i)).toBeInTheDocument();
            expect(screen.getByText(/Time: 14:45:00/i)).toBeInTheDocument();
            expect(screen.getByText(/Total Amount: 200/i)).toBeInTheDocument();
        });
    });
 
});
 