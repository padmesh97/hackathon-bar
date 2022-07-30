import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import * as M from 'materialize-css';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder:FormBuilder,private _http:HttpClient) {}

  loginForm = this.formBuilder.group({
  	email:['',Validators.required],
  	password:['',Validators.required]
  });

  API_URL= environment.API_URL;
  loginUrl=this.API_URL+'/login';
  checkLoginUrl=this.API_URL+'/check';

  

  ngOnInit(): void {
  }

  onSubmit(){
    var formData = {
      "email": this.loginForm.get('email')!.value,
      "password": this.loginForm.get('password')!.value
    };
    
    const headers = { 'content-type': 'application/json'} 

    this._http.post(this.loginUrl,formData,{'headers':headers}).subscribe(
      (response) => {
        if(response['status'] === 'success'){
          M.toast({html: response['status']});
        }
        else{
          this.handleError(response);
        }
      },
      (error) => {
        this.handleError(error);
      }
  	);
  }

  handleError(response){
    if(response.error.message !== undefined)
        M.toast({html: response.error.message, classes: 'rounded red'});
    else if(response.error.error.message !== undefined)
        M.toast({html: response.error.error.message, classes: 'rounded red'});
    else if(response['message'] !== null)
      M.toast({html: response['message'], classes: 'rounded red'});
  }

}
