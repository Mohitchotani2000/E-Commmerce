import './App.css';
import Home from './component/Home';
import { Route, Routes } from 'react-router-dom'
import Products from './component/Products';
import Product from './component/Product';
import Cart from './component/Cart';
import Signup from './component/Signup';
import Login from './component/Login';
import PrivateRoute from './component/PrivateRoute';
import Navbar from './component/Navbar';
import OrderHistory from './component/OrderHistory';

function App() {

  return (
    <>
    <Navbar/>
      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route exact path="/products" element={<Products/>} />
        <Route exact path="/products/:id" element={<Product/>} />
        <Route exact path="/cart" element={<Cart/>} />
        <Route exact path='/user' element={<PrivateRoute/>}>
        <Route exact path="cart" element={<Cart />} />
        <Route exact path="orderHistory" element={<OrderHistory/>} />
        </Route>
        <Route exact path="/register" element={<Signup/>} />
        <Route exact path="/login" element={<Login/>} />
      </Routes>
      </>
  );
}


export default App;
