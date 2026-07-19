import React, { useEffect, useState } from 'react';
import { useLocation, Link, useNavigate } from 'react-router-dom';
import { searchCustomers, searchProducts } from '../service/AdminService'
import { Row, Col, Card, Container } from 'react-bootstrap';

function SearchResults() {
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const location = useLocation();
    const navigator = useNavigate();


    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        const query = searchParams.get('query');
        const type = searchParams.get('type');

        console.log(query);

        const fetchResults = async () => {
            setLoading(true);
            setError(null);
            try {
                let response;
                if (type === 'customers') {
                    response = await searchCustomers(query);
                } else if (type === 'products') {
                    response = await searchProducts(query);
                }

                if (response && Array.isArray(response.data)) {
                    setResults(response.data);
                } else {
                    setResults([]);
                }
            } catch (error) {
                console.error('Error fetching search results:', error);
                setError('Error fetching search results');
                setResults([]);
            } finally {
                setLoading(false);
            }
        };

        fetchResults();
    }, [location.search]);

    return (
        <div className='searchresult allproducts'>
            <Container>
                <h3 className='my-4 text-center mb-5'>Search Results</h3>
                {loading ? (
                    <p>Loading...</p>
                ) : error ? (
                    <p>{error}</p>
                ) : results.length === 0 ? (
                    <p className='text-center'>No results found</p>
                ) : (
                    <Row>
                        {results.map(result => {
                            const isCustomer = location.search.includes('type=customers');
                            return (
                                <Col
                                key={isCustomer ? result.customerId : result.itemId}
                                md={isCustomer ? 12 : 3}
                                sm={6}
                                xs={12}
                                className="mb-3"
                                >
                                    <Link
                                        to={isCustomer ? `/customer/${result.customerId}` : `/productdetail/${result.itemId}`}
                                        style={{ textDecoration: 'none', color: 'inherit' }}
                                    >
                                        
                                            {isCustomer ? (
                                                <Card className="h-100 profile-card">
                                                <Card.Body>
                                                    <Card.Text>
                                                        <Card.Title>{result.userName}</Card.Title>
                                                        <strong>Name:</strong>  {result.name} &nbsp; &nbsp;
                                                        <strong>Phone:</strong> {result.phone}&nbsp; &nbsp;
                                                        <strong>Email:</strong> {result.email}
                                                    </Card.Text>
                                                </Card.Body>
                                                </Card>
                                            ) : (
                                                    <Card className='card1 mb-5 mr-4'>
                                                        <Card.Img className='custom-card-img'
                                                            variant="top"
                                                            src={result.imageUrl}
                                                            alt={result.itemName}
                                                        />
                                                        <Card.Body>
                                                            <Card.Title className='text-center mb-3'>{result.itemName}</Card.Title>
                                                            <Card.Text className='mb-1'>
                                                                <strong>Price:</strong> {result.itemPrice}
                                                            </Card.Text>
                                                            <Card.Text className='mb-1'>
                                                                <strong>Quantity:</strong> {result.itemQuantity}
                                                            </Card.Text>
                                                        </Card.Body>
                                                    </Card>
    
                                            )}
                                       
                                    </Link>
                                </Col>
                            );
                        })}
                    </Row>
                )}
            </Container>
        </div>
    );
}

export default SearchResults;

