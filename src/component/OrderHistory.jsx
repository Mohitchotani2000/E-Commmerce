import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import { getCurrentUserDetail } from '../auth';

const OrderHistory = () => {
  const [orders, setOrders] = useState([]);
  const [user, setUser] = useState(undefined);

  useEffect(() => {
    setUser(getCurrentUserDetail());
  }, [orders]);


  useEffect(() => {
    const fetchOrders = async () => {
      try {
        if (user) {
          const res = await axios.get(`http://localhost:8080/order-service/orders/user?userId=${user.userId}`);
          setOrders(res.data);
        }
      } catch (err) {
        console.log(err);
        setOrders([]);
      }
    };

    fetchOrders();
  }, [user]);

  const getOrderItems = (order) => {
    const groupedItems = {};
    let totalPrice = 0;

    order.orderItems.forEach((item) => {
      if (item.quantity > 0) {
        const productName = item.product.productName;
        const productPrice = item.product.productPrice;
        const quantity = item.quantity;
        const itemTotalPrice = productPrice * quantity;

        if (productName in groupedItems) {
          groupedItems[productName].quantity += quantity;
          groupedItems[productName].totalPrice += itemTotalPrice;
        } else {
          groupedItems[productName] = {
            productName: productName,
            productPrice: productPrice,
            quantity: quantity,
            totalPrice: itemTotalPrice,
          };
        }

        totalPrice += itemTotalPrice;
      }
    });

    return Object.values(groupedItems).map((item, index) => (
      <tr key={`${order.orderId}_${item.productName}_${index}`}>
        {index === 0 ? (
          <>
            <td rowSpan={Object.keys(groupedItems).length}>{order.orderId}</td>
            <td rowSpan={Object.keys(groupedItems).length}>{order.createdDate.toString().substring(0, 10)}</td>
            <td rowSpan={Object.keys(groupedItems).length}>{totalPrice}</td>
          </>
        ) : null}
        <td>{item.productName}</td>
        <td>{item.productPrice}</td>
        <td>{item.quantity}</td>
        <td>{item.totalPrice}</td>
      </tr>
    ));
  };

  return (
    <>
      <div className='container my-5 py-5 text-center'>
        <h2>Order History</h2>
        <Table striped bordered hover className='my-3 py-3'>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Date</th>
              <th>Total Price</th>
              <th>Product Name</th>
              <th>Product Price</th>
              <th>Quantity</th>
              <th>Item Price</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => getOrderItems(order))}
          </tbody>
        </Table>
      </div>
    </>
  );
};

export default OrderHistory;
