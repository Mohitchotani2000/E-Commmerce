import React, { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify';
import { loginUser } from '../services/user-service';
import { doLogin } from '../auth';

export default function Login() {

    let navigate = useNavigate();

   const[loginDetail,setLoginDetail] =  useState({
        username:'',
        password:''
    });

    const handleChange = (event,field) =>{
        let actualValue = event.target.value;
        setLoginDetail({
            ...loginDetail,
            [field]:actualValue
        })
    }

    const handleFormSubmit =  (event) =>{
        event.preventDefault();
        if(loginDetail.username.trim()==='' || loginDetail.password.trim===''){
            toast.error("Username or Password is required");
            return;
        }
        loginUser(loginDetail).then((data)=>{
            doLogin(data,()=>{
                navigate("/")
            })
            toast.success("Login Success !!");
        }).catch((error)=>{
            if(error.response.status===400 || error.response.status===404){
                toast.error(error.response.data.message);
            }else{
                toast.error("Something went wrong !!");
            }
        })
    }

  return (
    <>
    <section className="vh-100 bg-image">
                <div className="mask d-flex align-items-center h-100 gradient-custom-3">
                    <div className="container h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-9 col-lg-7 col-xl-6">
                                <div className="card border-dark" style={{ borderRadius: "15px" }}>
                                    <div className="card-body p-5">
                                        <h2 className="text-center mb-5">Sign in</h2>

                                        <form onSubmit={handleFormSubmit}>

                                            <div className="form-outline mb-4">
                                            <label className="form-label" htmlFor="username">Your Username</label>
                                                <input type="text" id="username" className="form-control form-control-lg" 
                                                value={loginDetail.username}
                                                onChange={(e)=>handleChange(e,'username')}/>
                                            </div>

                                            <div className="form-outline mb-4">
                                            <label className="form-label" htmlFor="password">Password</label>
                                                <input type="password" id="password" className="form-control form-control-lg" 
                                                value={loginDetail.password} 
                                                onChange={(e)=>handleChange(e,'password')}
                                                />
                                            </div>

                                            <div className="d-flex justify-content-center">
                                                <button type="submit"
                                                    className="btn btn-outline-primary btn-block btn-lg gradient-custom-4 text-body">Login</button>
                                            </div>

                                            <p className="text-center text-muted mt-5 mb-0">Dont have an account? <NavLink to="/register"
                                                className="fw-bold text-body"><u>Register here</u></NavLink></p>

                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
    </>
  )
}
