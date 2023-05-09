import React, { useEffect, useState } from 'react'
import { NavLink, useNavigate, useParams } from 'react-router-dom'
import axios from 'axios';
import Skeleton from 'react-loading-skeleton';
import { getCurrentUserDetail } from '../auth';

export default function Product() {
    const {id} = useParams();
    const [product,setProduct] = useState([]);
    const [loading,setLoading] = useState(false);
    const [user,setUser] = useState(undefined);
    let navigate = useNavigate();
    const [cartLength,setCartLength]=useState(0);

    useEffect(()=>{
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
        cartLengthUpdation();
    },[user])
   

    useEffect(()=>{
        const getProduct = async() =>{
            setLoading(true);
            setUser(getCurrentUserDetail());
           const response =  await axios.get(`http://localhost:8082/product-service/products/getProductDetail/${id}`);
           setProduct(await response.data);
           setLoading(false);
        } 
        getProduct();
    },[id]);

    const addProduct = async ()=>{
        await axios.post(`http://localhost:8083/cart-service/add?productId=${id}&userId=${user.userId}`,{"quantity":1}).then(()=>{
            setCartLength(cartLength+1);
             navigate("/user/cart");
        }
        );
    }

    const Loading = () =>{
        return(
            <>
                <div className="col-md-6" style={{lineHeight:2}}>
                    <Skeleton height={400}/>
                </div>
                <div className="col-md-6" style={{lineHeight:2}}>
                    <Skeleton height={50} width={300}/>
                    <Skeleton height={75}/>
                    <Skeleton height={50}/>
                    <Skeleton height={150}/>
                    <Skeleton height={50} width={100}/>
                    <Skeleton height={50} width={100} style={{marginLeft:6}}/>
                </div>
            </>
        )
    }

    const ShowProduct = () =>{
       return(
        <>
        <div className="col-md-6">
            <img src={product.productUrl} alt={product.productName} height="400px" width="400px"/>
        </div>
        <div className="col-md-6">
            <h4 className="text-uppercase text-black-50">
                {product.category}
            </h4>
            <h1 className="display-5">
                {product.productName}
            </h1>
           <h3 className="display-6 fw-bold my-4">
            Price: ₹ {product.productPrice}
           </h3>
           <p className="lead">
            {product.productDesc}
           </p>
           <button className="btn btn-outline-warning" onClick={()=> addProduct()}>
            Add to Cart
           </button>
           <NavLink to="/cart" className="btn btn-dark ms-2 px-3 py-2">
            Go to Cart
           </NavLink>
        </div>
        </>
       )
    }


    return (
    <div>
        <div className="container py-5">
            <div className="row py-4">
                {loading ?<Loading/> : <ShowProduct/>}
            </div>
        </div>
    </div>
  )
}