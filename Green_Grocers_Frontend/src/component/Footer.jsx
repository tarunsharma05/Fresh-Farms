import React from 'react'
import { MDBFooter, MDBContainer, MDBRow, MDBCol } from 'mdb-react-ui-kit';
import './style/Header.css';
import { NavLink } from 'react-router-dom';

function Footer() {
    return (

        <MDBFooter className='text-center footer1' color='white' bgColor='dark'>
            <MDBContainer className='p-4'>
                <MDBRow>

                    <MDBCol>
                        <h6 className='text-uppercase fw-bold'>Categories</h6>

                        <ul className="navbar-nav ml-auto">

                            <li className="nav-item active">
                                <NavLink exact to="/items/fruit" className="nav-link links">Fruits</NavLink>
                                <NavLink exact to="/items/vegetable" className="nav-link links">Vegetables</NavLink>
                            </li>

                        </ul>
                    </MDBCol>

                    <MDBCol>
                        <h6 className='text-uppercase fw-bold'>Contact</h6>

                        <ul className="navbar-nav ml-auto">
                            <li className="nav-item active">
                                <NavLink className="nav-link links"> Banglore, INDIA</NavLink>
                                <NavLink className="nav-link links">greengrocers@gmail.com</NavLink>
                            </li>
                        </ul>

                    </MDBCol>
                </MDBRow>
            </MDBContainer>

            <div className='text-center p-3' style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}>
                © 2024 Copyright:<> </>
                <a className='text-white'>
                    GreenGrocers.com
                </a>
            </div>
        </MDBFooter>


    )
}

export default Footer