import { faTimes, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react';
import { Container, Form, Button, Modal, Collapse } from 'react-bootstrap';
import { deleteUser } from '../../service/AdminService';
import { useNavigate, useParams } from 'react-router-dom';

function DeleteUser() {
  // State variables for managing form and modal visibility, selected reason, and additional feedback
  const [showForm, setShowForm] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [selectedReason, setSelectedReason] = useState('');
  const [additionalFeedback, setAdditionalFeedback] = useState('');

  // Get the customer ID from the URL parameters and decode it
  const { customerId: encodedCustomerId } = useParams();
  const decodedCustomerId = atob(encodedCustomerId);
  const navigate = useNavigate();

  // Handle the request to delete the account
  const handleDeleteRequest = () => {
    console.log(decodedCustomerId)
    setShowModal(true);
  };

  // Handle closing the modal
  const handleClose = () => {
    setShowModal(false);
  };

  // Toggle the visibility of the form
  const toggleForm = () => {
    setShowForm(!showForm);
  };

  // Confirm and execute the account deletion
  const handleConfirmDelete = async () => {
    try {
      const response = await deleteUser(decodedCustomerId);
      
      if (response.status === 200) {
        alert('Account deleted successfully');
        navigate("/"); // Redirect to home page after deletion
      } else {
        alert('Failed to delete account. Please try again later.');
      }
    } catch (error) {
      console.error('Error deleting account:', error);
      alert('An error occurred. Please try again later.');
    } finally {
      setShowModal(false);
    }
  };

  return (
    <Container className='mt-5 delete-user-container'>
      <div className='mb-5'>
        <h3 className='mb-4'>Account Privacy and Policy</h3>
        <p>
          We're sorry to see you go! Deleting your account will permanently remove all your data, including your profile information, order history, and saved preferences. Any pending orders will be canceled and refunded according to our refund policy. Some data may be retained for legal reasons. If you are certain about deleting your account, please click the button below. For any questions or assistance, contact us at [greengrocer@gmail.com].
        </p>
      </div>

      <Button variant="secondary" onClick={toggleForm} className="mb-3">
        <FontAwesomeIcon
          icon={faTrash}
          className="mr-3"
        />
        Request to delete account
      </Button>

      <Collapse in={showForm}>
        <div>
          <Form>
            <Form.Group controlId="reasonSelect" className="mb-3">
              <Form.Label>Why do you want to delete your account?</Form.Label>
              <Form.Control 
                as="select"
                value={selectedReason}
                onChange={(e) => setSelectedReason(e.target.value)}
              >
                <option value="">Select a reason...</option>
                <option value="Privacy concerns">Privacy concerns</option>
                <option value="Not using the service">Not using the service</option>
                <option value="Prefer another service">Prefer another service</option>
                <option value="Other">Other</option>
              </Form.Control>
            </Form.Group>
            {selectedReason === 'Other' && (
              <Form.Group controlId="additionalFeedback" className="mb-3">
                <Form.Label>Additional Feedback</Form.Label>
                <Form.Control 
                  as="textarea"
                  rows={3}
                  value={additionalFeedback}
                  onChange={(e) => setAdditionalFeedback(e.target.value)}
                />
              </Form.Group>
            )}
            <Button variant="danger" onClick={handleDeleteRequest}>Delete Account</Button>
          </Form>
        </div>
      </Collapse>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header>
          <Modal.Title>Confirm Account Deletion</Modal.Title>
          <Button variant='' onClick={handleClose}>
            <FontAwesomeIcon icon={faTimes} />
          </Button>
        </Modal.Header>
        <Modal.Body>
          <p>Are you sure you want to delete your account? This action cannot be undone.</p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Cancel</Button>
          <Button variant="danger" onClick={handleConfirmDelete}>
            Confirm Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default DeleteUser;
