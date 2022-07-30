import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as M from 'materialize-css';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'store';
  
  loggedInUser = {};

  API_URL= environment.API_URL;
  checkLoginUrl=this.API_URL+'/login/check';

  constructor(private http: HttpClient)
  {}

  ngOnInit(): void {
    this.checkAuthentication();
  }

  public checkAuthentication(){
    this.http.get(this.checkLoginUrl).subscribe(
      (response) => {
			if(response['status'] == 'success' && response['isAuthenticated']){
				this.loggedInUser = response['user'];
			}
      else{
        M.toast({html: 'Could not determine logged in user', classes: 'rounded red'});
      }
		},
		(error) =>{
			M.toast({html: 'Something went wrong', classes: 'rounded red'});
		});
  }


}


