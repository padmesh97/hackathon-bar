import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import * as M from 'materialize-css';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private formBuilder:FormBuilder,private _http:HttpClient) {}

  signupForm = this.formBuilder.group({
    type:['',Validators.required],
    name:['',Validators.required],
  	email:['',Validators.required],
  	password:['',Validators.required]
  });

  API_URL= environment.API_URL;
  signupUrl=this.API_URL+'/signup';

  elems;
	instances;

  ngOnInit(): void {
    this.elems = document.querySelectorAll('select');
    this.instances = M.FormSelect.init(this.elems, "");
  }

  onSubmit(){
    var type = this.signupForm.get('type')!.value;
    var name = this.signupForm.get('name')!.value;
    var email = this.signupForm.get('email')!.value;
    var password = this.signupForm.get('password')!.value;

    if(email == "" || name == "" || type == "" || password == ""){
      M.toast({html: 'Incomplete details', classes: 'rounded red'});
      return;
    }
    var formData = {
      "type": type,
      "name": name,
      "email": email,
      "password": password
    };
    
    const headers = { 'content-type': 'application/json'} 

    this._http.post(this.signupUrl,formData,{'headers':headers}).subscribe(
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
