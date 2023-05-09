
import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { getCurrentUserDetail } from '../auth';
import { NavLink, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

export default function Cart({ cartItems, onCartUpdate }) {

  const [cart, setCart] = useState([]);
  const [user, setUser] = useState(undefined);
  const [total, setTotal] = useState(0);
  let navigate = useNavigate();
  const [cartLength,setCartLength]=useState(0);   

  useEffect(() => {
    setUser(getCurrentUserDetail());
  }, []);


    const cartLengthUpdation = async () => {
        if(user){
            let temp;
            await axios
            .get(`http://localhost:8083/cart-service/getCartItems?userId=${user.userId}`)
            .then((res) => {
                temp = res.data.cartItems;
            });
            setCartLength(temp.length);
        }
    }

  useEffect(() => {
      cartLengthUpdation();
  }, [user]);

  useEffect(() => {
    const getCart = async () => {
      if (user) {
        const response = await axios.get(`http://localhost:8083/cart-service/getCartItems?userId=${user.userId}`);
        setCart(response.data.cartItems);
        setTotal(response.data.totalCost);
      }
    };
    getCart();
  }, [user]);

  const handleRemove = async (cartItemId) => {
    await axios.delete(`http://localhost:8083/cart-service/delete/${cartItemId}?userId=${user.userId}`);
    setCart(cart.filter(item => item.cartItemId !== cartItemId));
    setTotal(prevTotal =>
      prevTotal - cart.find(item => item.cartItemId === cartItemId).product.productPrice * cart.find(item => item.cartItemId === cartItemId).quantity
    );
    setCartLength(cart.length);
  };

  
  const placeOrder = async () => {
    await axios.post(`http://localhost:8080/order-service/orders/add?userId=${user.userId}`).then(()=>{
      setCart([])
      setCartLength(0)
      toast.success("Order Placed Successfully!!")
      navigate("/user/orderHistory")
    } 
    )
  }

  const deleteCart = async () => {
    await axios.delete(`http://localhost:8083/cart-service/delete?userId=${user.userId}`).then(()=>{
      setCart([]);
      setCartLength(0);
      setTotal(0);
      navigate("/user/cart");
    });
  }

  const handleQuantityChange = async (cartItemId, quantity) => {
    await axios.put(`http://localhost:8083/cart-service/update/${cartItemId}?userId=${user.userId}`, { quantity });
    setCart(prevCart =>
      prevCart.map(item => (item.cartItemId === cartItemId ? { ...item, quantity } : item))
    );
    setTotal(prevTotal => {
      const updatedItem = cart.find(item => item.cartItemId === cartItemId);
      const updatedTotal = prevTotal - (updatedItem.quantity * updatedItem.product.productPrice) + (quantity * updatedItem.product.productPrice);
      return updatedTotal;
    });
  };


  const ShowCart = () => {

    return (
      cart.map((items) => {
        return (
          <React.Fragment key={items.cartItemId}>
            <div className="cartProductContainer row">
              <img
                src={items.product.productUrl}
                className="img-fluid col-3 p-2"
                alt={items.product.productName}
              />
              <div className="cartProductDescription col-7 p-4 mx-auto">
                <p className='fw-bolder'>{items.product.productName}</p>
                <p>
                  Quantity:
                  <br />
                  <button className="btn btn-light m-2" disabled={items.quantity <= 1}
                    onClick={() => handleQuantityChange(items.cartItemId, items.quantity - 1)} >
                    -
                  </button>
                  <span> {items.quantity}</span>
                  <button className="btn btn-light m-2" onClick={() => handleQuantityChange(items.cartItemId, items.quantity + 1)}>
                    +
                  </button>
                </p>
                <p>Item - Price: ₹ {items.product.productPrice}</p>
                <p>Sub - Total: ₹ {items.product.productPrice * items.quantity}</p>
              </div>
              <div
                className="col-md-2 d-flex p-2"
                style={{ flexDirection: "column", justifyContent: "space-around" }}
              >
                <button className="btn btn-outline-danger" onClick={() => handleRemove(items.cartItemId)}>
                  Remove
                </button>
              </div>
            </div>
          </React.Fragment>
        )
      })
    )
  }

  return (
    <>
      <div>
        <div className="container my-5 py-5">
          <div className="row">
            <div className="col-12 mb-5">
              <h1 className="display-6 fw-bolder text-center">My Cart</h1>
              <hr />
            </div>
          </div>
          <div className="row justify-content-center">
            {cart.length === 0 && (
              <div className="emptyCart container text-center">
                <h1 className="row heading m-3 text-center">
                  Your Cart is Empty...!
                </h1>
                <div className="row text-center">
                  <NavLink to="/products" className="btn btn-primary btn-lg">
                    Start Shopping
                  </NavLink>
                </div>
              </div>
            )}
            {
              cart.length !== 0 && (
                <>
                  <ShowCart />
                  <h3 className='text-center fw-bolder mx-auto' >Cart Total: ₹{total}</h3>
                  <div className="col-sm-12 text-center my-2">
                    <button  className="btn btn-primary btn-md center-bloc ms-2" onClick={() => placeOrder()}>Place Order</button>
                    <button className="btn btn-danger btn-md center-block ms-2" onClick={() => deleteCart()}>Delete Cart</button>
                  </div>
                  <div className="">
                  <NavLink to="/products" className="">
                      Continue Shopping
                  </NavLink>
                  </div>
                </>
              )
            }
          </div>
        </div>
      </div>
    </>
  )
}
