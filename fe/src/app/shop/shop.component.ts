import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as M from 'materialize-css';
import { fromEvent, Observable, merge} from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, switchMap,  tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

  constructor(private http: HttpClient) { }

  API_URL= environment.API_URL;
  searchUrl = this.API_URL+"/search?query=";

  debounceTime:number=4000;
	searchQuery:String="";
	minSearchCharCount:number=0;

  shopItems = [];

  ngOnInit(): void {
    
    var searchInput = document.getElementById('search_product')!;

    this.loadInitialList();

    const typeahead = fromEvent(searchInput, 'keyup').pipe(
		  map((e: Event) => (e.target as HTMLInputElement).value),
		  tap((searchTerm) => {
		  	this.searchQuery=searchTerm
		  }),
		  filter(text => text.trim().length >= this.minSearchCharCount),
		  debounceTime(this.debounceTime),
		  distinctUntilChanged(),
		  tap((searchTerm) => {
		  	  this.searchQuery=searchTerm
		  	}
		  ),
		  switchMap(searchTerm =>
		  	this.http.get(this.searchUrl+encodeURI(this.searchQuery.trim())))
		  );

		typeahead.subscribe(data => {
			//console.log(data);
			this.handleHttpData(data);
		},
		error =>
		{
			M.toast({html: 'Something went wrong', classes: 'rounded red'});
		}
		);

  }

  handleHttpData(response){
		if(response['status']=='success'){
			this.shopItems = response['items'];
		}
		else{
			M.toast({html: 'Something went wrong', classes: 'rounded red'});
		}
	}

  loadInitialList(){
    this.http.get(this.searchUrl).subscribe(
      (response) => {
			if(response['status'] == 'success' ){
				this.shopItems = response['items'];
			}
      else{
        M.toast({html: 'Could not load products', classes: 'rounded red'});
      }
		},
		(error) =>{
			M.toast({html: 'Something went wrong', classes: 'rounded red'});
		});
  }



}
