import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import { getCustomerById, updateCustomerDetails } from '../../service/AdminService';

const UserProfile = ({ customerId, onClose, onUpdate }) => {
    // State to manage form data and validation errors
    const [formData, setFormData] = useState({
        userName: '',
        name: '',
        email: '',
        phone: ''
    });
    const [errors, setErrors] = useState({});

    // Fetch customer details when component mounts or customerId changes
    useEffect(() => {
        getCustomerById(customerId)
            .then(response => {
                setFormData({
                    userName: response.data.userName,
                    name: response.data.name,
                    email: response.data.email,
                    phone: response.data.phone
                });
            })
            .catch(error => console.error('Error fetching customer details:', error));
    }, [customerId]);

    // Handle input change events
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Validate form data
    const validateForm = () => {
        const newErrors = {};
        if (!formData.userName) newErrors.userName = 'Username is required';
        if (!formData.name) {
            newErrors.name = 'Name is required';
        } else if (formData.name.length < 2) {
            newErrors.name = 'Name must be at least 2 characters long';
        }
        if (!formData.email) {
            newErrors.email = 'Email is required';
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newErrors.email = 'Email is invalid';
        }
        if (!formData.phone) {
            newErrors.phone = 'Phone number is required';
        } else if (!/^\d{10}$/.test(formData.phone)) {
            newErrors.phone = 'Phone number must be exactly 10 digits';
        }
        return newErrors;
    };

    // Handle form submission
    const handleSaveChanges = (e) => {
        e.preventDefault();
        const validationErrors = validateForm();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
        } else {
            updateCustomerDetails(customerId, formData)
                .then(response => {
                    onUpdate(response.data); // Notify parent component about the update
                    onClose(); // Close the form/modal
                })
                .catch(error => console.error('Error updating customer details:', error));
        }
    };

    return (
        <Form onSubmit={handleSaveChanges}>
            <Form.Group controlId="formUserName">
                <Form.Label>Username</Form.Label>
                <Form.Control
                    type="text"
                    name="userName"
                    disabled={true}
                    value={formData.userName}
                    onChange={handleInputChange}
                    isInvalid={!!errors.userName}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.userName}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formName">
                <Form.Label>Name</Form.Label>
                <Form.Control
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleInputChange}
                    isInvalid={!!errors.name}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.name}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control
                    type="email" // Corrected type to "email"
                    name="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    isInvalid={!!errors.email}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.email}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formPhone">
                <Form.Label>Phone</Form.Label>
                <Form.Control
                    className='mb-2'
                    type="text"
                    name="phone"
                    value={formData.phone}
                    onChange={handleInputChange}
                    isInvalid={!!errors.phone}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.phone}
                </Form.Control.Feedback>
            </Form.Group>
            <Button variant="primary" type="submit">
                Save Changes
            </Button>
        </Form>
    );
};

export default UserProfile;
