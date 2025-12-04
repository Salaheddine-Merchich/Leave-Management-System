import { Component } from '@angular/core';
import { UserService, User } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-employee-info',
  imports: [CommonModule],
  templateUrl: './employee-info.component.html',
  styleUrl: './employee-info.component.css'
})
export class EmployeeInfoComponent {
  user:any;

  constructor(private userService:UserService,private route:ActivatedRoute){}



  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);
    if(id){


  
    this.userService.getUserById(+id).subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (error) => {
        console.error('Error findinf tasks:', error);
      }
    });
        console.log('kat3ayat');
  }
  }
  

}
