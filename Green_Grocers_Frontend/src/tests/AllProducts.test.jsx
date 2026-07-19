import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import AllProducts from '../component/item/AllProducts';
import { allproductList, sortByName, sortByPrice } from '../service/ItemServices';
import { BrowserRouter } from 'react-router-dom';

// Mock the services
vi.mock('../service/ItemServices', () => ({
  allproductList: vi.fn(),
  sortByName: vi.fn(),
  sortByPrice: vi.fn(),
}));

describe('AllProducts', () => {
  it('renders without crashing and displays products', async () => {
    const mockProducts = [
      {
        itemId: '1',
        itemName: 'Product 1',
        itemPrice: 100,
        itemQuantity: 10,
        imageUrl: 'https://via.placeholder.com/150',
      },
      {
        itemId: '2',
        itemName: 'Product 2',
        itemPrice: 200,
        itemQuantity: 20,
        imageUrl: 'https://via.placeholder.com/150',
      },
    ];

    allproductList.mockResolvedValue({ data: mockProducts });

    render(
      <BrowserRouter>
        <AllProducts />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Product 1')).toBeInTheDocument();
      expect(screen.getByText('Product 2')).toBeInTheDocument();
    });
  });

  it('displays no products found message when no products are available', async () => {
    allproductList.mockResolvedValue({ data: [] });

    render(
      <BrowserRouter>
        <AllProducts />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('No products found')).toBeInTheDocument();
    });
  });

  it('sorts products by price when Sort By Price is clicked', async () => {
    const mockProductsSortedByPrice = [
      {
        itemId: '1',
        itemName: 'Product 1',
        itemPrice: 50,
        itemQuantity: 10,
        imageUrl: 'https://via.placeholder.com/150',
      },
      {
        itemId: '2',
        itemName: 'Product 2',
        itemPrice: 150,
        itemQuantity: 20,
        imageUrl: 'https://via.placeholder.com/150',
      },
    ];

    allproductList.mockResolvedValue({ data: [] });
    sortByPrice.mockResolvedValue({ data: mockProductsSortedByPrice });

    render(
      <BrowserRouter>
        <AllProducts />
      </BrowserRouter>
    );

    fireEvent.click(screen.getByText('Sort By'));
    fireEvent.click(screen.getByText('Sort By Price'));

    await waitFor(() => {
      expect(screen.getByText('Product 1')).toBeInTheDocument();
      expect(screen.getByText('Product 2')).toBeInTheDocument();
    });
  });

  it('sorts products by name when Sort By Name is clicked', async () => {
    const mockProductsSortedByName = [
      {
        itemId: '1',
        itemName: 'Apple',
        itemPrice: 100,
        itemQuantity: 10,
        imageUrl: 'https://via.placeholder.com/150',
      },
      {
        itemId: '2',
        itemName: 'Banana',
        itemPrice: 200,
        itemQuantity: 20,
        imageUrl: 'https://via.placeholder.com/150',
      },
    ];

    allproductList.mockResolvedValue({ data: [] });
    sortByName.mockResolvedValue({ data: mockProductsSortedByName });

    render(
      <BrowserRouter>
        <AllProducts />
      </BrowserRouter>
    );

    fireEvent.click(screen.getByText('Sort By'));
    fireEvent.click(screen.getByText('Sort By Name'));

    await waitFor(() => {
      expect(screen.getByText('Apple')).toBeInTheDocument();
      expect(screen.getByText('Banana')).toBeInTheDocument();
    });
  });
});
