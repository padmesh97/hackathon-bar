import { Component, OnInit } from '@angular/core';
import * as M from 'materialize-css';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  constructor() { }

  elems;
	instances;

  ngOnInit(): void {

    this.elems = document.querySelectorAll('.sidenav');
    this.instances = M.Sidenav.init(this.elems, "");
  }

}
