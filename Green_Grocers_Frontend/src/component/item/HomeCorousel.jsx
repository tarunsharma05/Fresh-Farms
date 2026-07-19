import React from 'react'
import { Carousel } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../style/Item.css';

function HomeCorousel() {

    const navigator = useNavigate();

  return (
    <div>
    <Carousel fade className="custom-carousel" interval={3000}>
            <Carousel.Item onClick={() => navigator('/items')}>
                <img
                    className="d-block w-70"
                    src='src/assets/images/FARMER2.png'
                    alt="First slide"
                />
            </Carousel.Item>
            <Carousel.Item onClick={() => navigator('/items')}>
                <img
                    className="d-block w-100"
                    src='src/assets/images/FARMER5.png'
                    alt="Second slide"
                />
            </Carousel.Item>
            <Carousel.Item onClick={() => navigator('/items')}>
                <img
                    className="d-block w-100"
                    src='src/assets/images/FARMER4.png'
                    alt="Third slide"
                />
            </Carousel.Item>
        </Carousel>
        </div>
  )
}

export default HomeCorousel