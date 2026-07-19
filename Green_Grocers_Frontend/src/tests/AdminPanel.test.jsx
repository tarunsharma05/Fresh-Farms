import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { describe, it, expect, vi } from 'vitest';
import AdminPanel from '../component/admin/AdminPanel';
import { getAllCustomers, allproductList } from '../service/AdminService';
import { paymentList } from '../service/PaymentService';

// Mock the services
vi.mock('../service/AdminService', () => ({
  getAllCustomers: vi.fn(),
  allproductList: vi.fn(),
}));

vi.mock('../service/PaymentService', () => ({
  paymentList: vi.fn(),
}));

describe('AdminPanel', () => {
    
  it('renders card titles correctly', () => {
    getAllCustomers.mockResolvedValue({ data: [] });
    allproductList.mockResolvedValue({ data: [] });
    paymentList.mockResolvedValue({ data: [] });

    render(
      <Router>
        <AdminPanel />
      </Router>
    );

    expect(screen.getByText('Active Users')).toBeInTheDocument();
    expect(screen.getByText('Total Orders')).toBeInTheDocument();
    expect(screen.getByText('Total items')).toBeInTheDocument();
    expect(screen.getByText('Total Payments')).toBeInTheDocument();
    expect(screen.getByText('Undelivered Orders')).toBeInTheDocument();
    expect(screen.getByText('Total Revenue')).toBeInTheDocument();
    expect(screen.getByText('Most Ordered Item')).toBeInTheDocument();
  });
});
