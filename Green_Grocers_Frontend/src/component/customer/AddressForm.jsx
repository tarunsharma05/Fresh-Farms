import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import { addAddress, getAddressesById, updateAddress } from '../../service/AdminService';

function AddressForm({ addressId, onClose, customerId, onUpdate }) {
    // State to hold form data
    const [formData, setFormData] = useState({
        houseNo: '',
        landmark: '',
        city: '',
        state: '',
        country: '',
        pinCode: ''
    });

    // State to hold form validation errors
    const [errors, setErrors] = useState({});

    // Fetch address data if editing an existing address
    useEffect(() => {
        if (addressId !== null) {
            getAddressesById(addressId)
                .then((response) => {
                    const { houseNo, landmark, city, state, country, pinCode } = response.data;
                    setFormData({ houseNo, landmark, city, state, country, pinCode });
                })
                .catch(error => {
                    console.error('Error fetching address data:', error);
                });
        } else {
            // Reset form data for adding a new address
            setFormData({
                houseNo: '',
                landmark: '',
                city: '',
                state: '',
                country: '',
                pinCode: ''
            });
        }
    }, [addressId]);

    // Handle input change and update form data state
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    // Validate form data
    const validateForm = () => {
        const newErrors = {};
        if (!formData.houseNo) newErrors.houseNo = 'House Number is required';
        if (!formData.landmark) newErrors.landmark = 'Landmark is required';
        if (!formData.city) newErrors.city = 'City is required';
        if (!formData.state) newErrors.state = 'State is required';
        if (!formData.country) newErrors.country = 'Country is required';
        if (!formData.pinCode) {
            newErrors.pinCode = 'Pin Code is required';
        } else if (!/^\d{6}$/.test(formData.pinCode)) {
            newErrors.pinCode = 'Pin Code must be exactly 6 digits';
        }
        return newErrors;
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        const validationErrors = validateForm();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
        } else {
            if (addressId === null) {
                // Add new address logic
                addAddress(customerId, formData)
                    .then((response) => {
                        console.log("Address added:", response.data);
                        onUpdate(); // Call the callback function to update the address list
                        onClose(); // Close the form
                    })
                    .catch(error => {
                        console.error('Error adding address:', error);
                    });
            } else {
                // Update existing address logic
                updateAddress(addressId, formData)
                    .then((response) => {
                        console.log("Address updated:", response.data);
                        onUpdate(); // Call the callback function to update the address list
                        onClose(); // Close the form
                    })
                    .catch(error => {
                        console.error('Error updating address:', error);
                    });
            }
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formHouseNo">
                <Form.Label>House Number</Form.Label>
                <Form.Control
                    type="text"
                    name="houseNo"
                    value={formData.houseNo}
                    onChange={handleChange}
                    isInvalid={!!errors.houseNo}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.houseNo}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formLandmark">
                <Form.Label>Landmark</Form.Label>
                <Form.Control
                    type="text"
                    name="landmark"
                    value={formData.landmark}
                    onChange={handleChange}
                    isInvalid={!!errors.landmark}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.landmark}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formCity">
                <Form.Label>City</Form.Label>
                <Form.Control
                    type="text"
                    name="city"
                    value={formData.city}
                    onChange={handleChange}
                    isInvalid={!!errors.city}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.city}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formState">
                <Form.Label>State</Form.Label>
                <Form.Control
                    type="text"
                    name="state"
                    value={formData.state}
                    onChange={handleChange}
                    isInvalid={!!errors.state}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.state}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formCountry">
                <Form.Label>Country</Form.Label>
                <Form.Control
                    type="text"
                    name="country"
                    value={formData.country}
                    onChange={handleChange}
                    isInvalid={!!errors.country}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.country}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formPinCode">
                <Form.Label>Pin Code</Form.Label>
                <Form.Control
                    className='mb-2'
                    type="text"
                    name="pinCode"
                    value={formData.pinCode}
                    onChange={handleChange}
                    isInvalid={!!errors.pinCode}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.pinCode}
                </Form.Control.Feedback>
            </Form.Group>
            <Button variant="primary" type="submit" className='mr-2'>
                {addressId === null ? 'Add Address' : 'Update Address'}
            </Button>
            <Button variant="secondary" onClick={onClose}>
                Cancel
            </Button>
        </Form>
    );
}

export default AddressForm;
