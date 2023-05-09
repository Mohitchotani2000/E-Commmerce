import React, { useEffect, useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom';
import { doLogout, getCurrentUserDetail, isLoggedIn } from '../auth';
import axios from 'axios';

export default function Navbar() {

    let navigate = useNavigate();
    const [login, setLogin] = useState(false);
    const [user, setUser] = useState(undefined);
    const [cartLength,setCartLength]=useState(0);

    const cartLengthUpdation = async () => {
        if(user){
            let temp;
            await axios
            .get(`http://localhost:8083/cart-service/getCartItems?userId=${user.userId}`)
            .then((res) => {
                temp = res.data.cartItems;
            });
            setCartLength(temp.length);
        }else{
            setCartLength(0);
        }
        
    };
    
    useEffect(() => {
        setLogin(isLoggedIn());
        setUser(getCurrentUserDetail());
         // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [isLoggedIn()]);
    
    useEffect(() => {
        
        cartLengthUpdation();
        
    }, [user,cartLengthUpdation]);
    
    const logout = () => {
        doLogout(() => {
            setLogin(false);
            setUser(undefined);
            cartLengthUpdation();
            navigate("/login");
        });
        setUser(getCurrentUserDetail());
    };


    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light bg-white py-3 shadow-sm">
                <div className="container">
                    <NavLink className="navbar-brand fw-bold fs-4" to="/">BAZAAR</NavLink>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" >
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav mx-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <NavLink className="nav-link active" aria-current="page" to="/">Home</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/products">Products</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/about">About</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/contact">Contact</NavLink>
                            </li>
                        </ul>

                        <ul className="nav navbar-nav ms-auto">
                            <NavLink to="/user/cart" className=" btn  text-reset ">
                                <i className="fa fa-shopping-cart"></i> Cart ({cartLength})
                            </NavLink>

                           {
                                login && (
                                    <>
                                        <li className="nav-item dropdown">
                                            <NavLink to="#" className="nav-link dropdown-toggle" data-bs-toggle="dropdown">{user.username}</NavLink>
                                            <div className="dropdown-menu dropdown-menu-end">
                                                <NavLink to="/user/profile" className="dropdown-item">My Profile</NavLink>
                                                <NavLink to="/user/orderHistory" className="dropdown-item">My Orders</NavLink>
                                                <div className="dropdown-divider"></div>
                                                <button onClick={logout} className="dropdown-item">Logout</button>
                                            </div>
                                        </li>

                                    </>

                                )
                            }
                            {
                                !login && (
                                    <>
                                        <div className="buttons">
                                            <NavLink to="/login" className="btn ms-2 btn-outline-dark">
                                                <i className="fa fa-sign-in me-1"></i> Login
                                            </NavLink>
                                            <NavLink to="/register" className="btn btn-outline-dark ms-2">
                                                <i className="fa fa-user-plus"></i> Register
                                            </NavLink>
                                        </div>
                                    </>
                                )
                            }
                        </ul>
                    </div>
                </div>
            </nav>
        </div>

    )
}
