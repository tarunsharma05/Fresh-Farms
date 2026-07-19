import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { getAddressesByCustomerId, deleteAddressById } from '../service/AdminService';
import AddressList from '../component/customer/AddressList';
import { UserProvider } from '../component/context/UserContext';

// Mock the services
vi.mock('../service/AdminService', () => ({
  getAddressesByCustomerId: vi.fn(),
  deleteAddressById: vi.fn(),
}));

// Mock useUser to return a specific customerId
vi.mock('../context/UserContext', () => ({
  useUser: () => ({
    customerId: '1',
  }),
  UserProvider: ({ children }) => children,
}));

describe('AddressList', () => {
  it('renders without crashing and shows addresses', async () => {
    const mockAddresses = [
      {
        addressId: '1',
        houseNo: '123',
        landmark: 'Near Park',
        city: 'Springfield',
        pinCode: '123456',
        state: 'IL',
        country: 'USA',
      },
      {
        addressId: '2',
        houseNo: '456',
        landmark: 'Near Mall',
        city: 'Shelbyville',
        pinCode: '654321',
        state: 'IL',
        country: 'USA',
      },
    ];

    getAddressesByCustomerId.mockResolvedValue({ data: mockAddresses });

    render(
      <UserProvider>
        <AddressList />
      </UserProvider>
    );

    await waitFor(() => {
      expect(screen.getByText('Springfield')).toBeInTheDocument();
      expect(screen.getByText('Shelbyville')).toBeInTheDocument();
    });
  });

});
