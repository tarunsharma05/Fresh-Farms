import React, { createContext, useEffect, useState, useContext } from 'react';
import { addItemToCart, clearCart, fetchCart, removeItemFromCart } from '../../service/CartService';
import { useUser } from "./UserContext";
 
// Create a context for the cart
export const CartContext = createContext();
 
// CartProvider component to wrap around the part of the app that needs cart data
export const CartProvider = ({ children }) => {
  // Get the customerId from the UserContext
  const { customerId } = useUser();
 
  // State to manage the cart data
  const [cart, setCart] = useState(() => {
    // Initialize the cart from localStorage if available
    const savedCart = localStorage.getItem('cart');
    return savedCart ? JSON.parse(savedCart) : { items: [], totalAmount: 0 };
  });
 
  // State to manage items saved for later
  const [savedForLater, setSavedForLater] = useState(() => {
    // Initialize saved items from localStorage if available
    const savedItems = localStorage.getItem('savedForLater');
    return savedItems ? JSON.parse(savedItems) : [];
  });
 
  // State to manage recently viewed items
  const [recentlyViewed, setRecentlyViewed] = useState(() => {
    // Initialize recently viewed items from localStorage if available
    const viewedItems = localStorage.getItem('recentlyViewed');
    return viewedItems ? JSON.parse(viewedItems) : [];
  });
 
  // useEffect to load the cart from the server when the customerId changes
  useEffect(() => {
    const loadCart = async () => {
      try {
        const fetchedCart = await fetchCart(customerId);
        if (fetchedCart.items.length === 0) {
          localStorage.removeItem('cart');
        }
        // setCart(fetchedCart);
      } catch (error) {
        console.error('Error fetching cart:', error);
      }
    };
 
    if (customerId) {
      loadCart();
    }
  }, [customerId]);
 
  // useEffect to save the cart to localStorage whenever it changes
  useEffect(() => {
    if (cart.items.length === 0) {
      localStorage.removeItem('cart');
    } else {
      localStorage.setItem('cart', JSON.stringify(cart));
    }
  }, [cart]);
 
  // useEffect to save the savedForLater items to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('savedForLater', JSON.stringify(savedForLater));
  }, [savedForLater]);
 
  // useEffect to save the recentlyViewed items to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('recentlyViewed', JSON.stringify(recentlyViewed));
  }, [recentlyViewed]);
 
  // Function to handle adding an item to the cart
  const handleAddItemToCart = async (item) => {
    await addItemToCart(customerId, item.itemId);
    setCart((prevCart) => {
      const existingItem = prevCart.items.find(cartItem => cartItem.itemId === item.itemId);
      if (existingItem) {
        const updatedItems = prevCart.items.map(cartItem =>
          cartItem.itemId === item.itemId ? { ...cartItem, quantity: cartItem.quantity + 1 } : cartItem
        );
        return {
          ...prevCart,
          items: updatedItems,
          totalAmount: prevCart.totalAmount + item.itemPrice,
        };
      } else {
        const updatedItem = { ...item, quantity: 1 };
        return {
          ...prevCart,
          items: [...prevCart.items, updatedItem],
          totalAmount: prevCart.totalAmount + item.itemPrice,
        };
      }
    });
  };
 
  // Function to handle removing an item from the cart
  const handleRemoveItemFromCart = async (itemId) => {
    await removeItemFromCart(customerId, itemId);
    setCart((prevCart) => {
      const existingItem = prevCart.items.find(cartItem => cartItem.itemId === itemId);
      if (existingItem.quantity > 1) {
        const updatedItems = prevCart.items.map(cartItem =>
          cartItem.itemId === itemId ? { ...cartItem, quantity: cartItem.quantity - 1 } : cartItem
        );
        return {
          ...prevCart,
          items: updatedItems,
          totalAmount: prevCart.totalAmount - existingItem.itemPrice,
        };
      } else {
        const updatedItems = prevCart.items.filter(cartItem => cartItem.itemId !== itemId);
        return {
          ...prevCart,
          items: updatedItems,
          totalAmount: prevCart.totalAmount - existingItem.itemPrice,
        };
      }
    });
  };
 
  // Function to handle clearing the cart
  const handleClearCart = async () => {
    await clearCart(customerId);
    setCart({ items: [], totalAmount: 0 });
    localStorage.removeItem('cart');
  };
 
  // Function to handle saving an item for later
  const handleSaveForLater = (item) => {
    setSavedForLater((prevSaved) => [...prevSaved, item]);
    handleRemoveItemFromCart(item.itemId);
  };
 
  // Function to handle moving an item from saved for later back to the cart
  const handleMoveToCart = (item) => {
    handleAddItemToCart(item);
    setSavedForLater((prevSaved) => prevSaved.filter(savedItem => savedItem.itemId !== item.itemId));
  };
 
  // Function to handle removing an item from saved for later
  const handleRemoveFromSavedForLater = (itemId) => {
    setSavedForLater((prevSaved) => prevSaved.filter(savedItem => savedItem.itemId !== itemId));
  };
 
  // Function to handle adding an item to the recently viewed list
  const handleViewItem = (item) => {
    setRecentlyViewed((prevViewed) => {
      const updatedViewed = prevViewed.filter(viewedItem => viewedItem.itemId !== item.itemId);
      return [item, ...updatedViewed.slice(0, 4)]; // Keep only the latest 5 items
    });
  };
 
  return (
<CartContext.Provider value={{
      cart,
      savedForLater,
      recentlyViewed,
      handleAddItemToCart,
      handleRemoveItemFromCart,
      handleClearCart,
      handleSaveForLater,
      handleMoveToCart,
      handleRemoveFromSavedForLater,
      handleViewItem
    }}>
      {children}
</CartContext.Provider>
  );
};