
import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router, MemoryRouter } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import { getPaymentsByCustomerId } from '../service/PaymentService';
import CustomerPaymentsHistory from '../component/payment/CustomerPaymentsHistory';
 
// Mock the getPaymentsByCustomerId service
vi.mock('../service/PaymentService', () => ({
    getPaymentsByCustomerId: vi.fn(), // Mocking the service function
}));
 
// Begin test suite for CustomerPaymentsHistory component
describe('CustomerPaymentsHistory', () => {
 
    // Test case to check loading state while fetching customer payments
    it('displays loading state while fetching customer payments', async () => {
        // Mock an empty array response for customer payments
        getPaymentsByCustomerId.mockResolvedValueOnce({ data: [] });
   
        // Render the CustomerPaymentsHistory component within MemoryRouter
        render(
            <MemoryRouter>
                <CustomerPaymentsHistory />
            </MemoryRouter>
        );
   
        // Wait for the loading state to disappear
        await waitFor(() => {
            // Assert that the loading text is not present in the document
            expect(screen.queryByText(/Loading.../i)).not.toBeInTheDocument();
        });
    });
   
    // Test case to check if customer payment history is displayed correctly
    it('displays customer payment history', async () => {
        // Mock data for customer payments
        const mockPayments = [
            { paymentId: 1, paymentStatus: 'Payment Successful', paymentMode: 'Card', totalAmount: 100 },
            { paymentId: 2, paymentStatus: 'Payment Failed', paymentMode: 'UPI', totalAmount: 50 },
        ];
   
        // Mock the service function to return the mock data
        getPaymentsByCustomerId.mockResolvedValueOnce({ data: mockPayments });
   
        // Render the CustomerPaymentsHistory component within MemoryRouter
        render(
            <MemoryRouter>
                <CustomerPaymentsHistory />
            </MemoryRouter>
        );
   
        // Wait for the DOM to update
        await waitFor(() => {
            // Assert that details of both payments are displayed correctly
            expect(screen.getByText(/Payment ID: 1/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Successful/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Mode: Card/i)).toBeInTheDocument();
            expect(screen.getByText(/Total Amount: 100/i)).toBeInTheDocument();
   
            expect(screen.getByText(/Payment ID: 2/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Failed/i)).toBeInTheDocument();
            expect(screen.getByText(/Payment Mode: UPI/i)).toBeInTheDocument();
            expect(screen.getByText(/Total Amount: 50/i)).toBeInTheDocument();
        });
    });
});
 