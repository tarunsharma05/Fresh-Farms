import { render, screen, fireEvent } from '@testing-library/react';
import { CartContext } from '../component/context/CartContext';
import { describe, it, expect, vi } from 'vitest';
import CartItem from '../component/cart/CartItem';

// Mock CartContext value
const mockCartContextValue = {
  handleViewItem: vi.fn(),
};

describe('CartItem', () => {
  

  it('calls removeItemFromCart when "-" button is clicked', () => {
    const item = {
      itemId: 1,
      imageUrl: 'example.jpg',
      itemName: 'Example Item',
      itemPrice: 10,
      quantity: 2,
    };

    const removeItemFromCartMock = vi.fn();

    render(
      <CartContext.Provider value={mockCartContextValue}>
        <CartItem item={item} removeItemFromCart={removeItemFromCartMock} />
      </CartContext.Provider>
    );

    fireEvent.click(screen.getByText('-'));

    // Check if removeItemFromCartMock is called with correct arguments
    expect(removeItemFromCartMock).toHaveBeenCalledWith(1);
  });

  it('calls addItemToCart when "+" button is clicked', () => {
    const item = {
      itemId: 1,
      imageUrl: 'example.jpg',
      itemName: 'Example Item',
      itemPrice: 10,
      quantity: 2,
    };

    const addItemToCartMock = vi.fn();

    render(
      <CartContext.Provider value={mockCartContextValue}>
        <CartItem item={item} addItemToCart={addItemToCartMock} />
      </CartContext.Provider>
    );

    fireEvent.click(screen.getByText('+'));

    // Check if addItemToCartMock is called with correct arguments
    expect(addItemToCartMock).toHaveBeenCalledWith(item);
  });

  it('calls saveForLater when "Save for Later" button is clicked', () => {
    const item = {
      itemId: 1,
      imageUrl: 'example.jpg',
      itemName: 'Example Item',
      itemPrice: 10,
      quantity: 2,
    };

    const saveForLaterMock = vi.fn();

    render(
      <CartContext.Provider value={mockCartContextValue}>
        <CartItem item={item} saveForLater={saveForLaterMock} />
      </CartContext.Provider>
    );

    fireEvent.click(screen.getByText('Save for Later'));

    // Check if saveForLaterMock is called with correct arguments
    expect(saveForLaterMock).toHaveBeenCalledWith(item);
  });


});
