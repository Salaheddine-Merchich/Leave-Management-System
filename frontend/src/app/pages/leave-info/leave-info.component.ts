import { Component,OnInit } from '@angular/core';
import { LeaveRequest,LeaveService } from '../../services/leave.service';
import { RouterModule,ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-leave-info',
  imports: [CommonModule, RouterModule],
  templateUrl: './leave-info.component.html',
  styleUrl: './leave-info.component.css'
})
export class LeaveInfoComponent {
  leaveRequest:any;

  constructor(private leaveService : LeaveService,private route:ActivatedRoute ){}


  ngOnInit():void{
    const id =this.route.snapshot.paramMap.get('id');
    console.log("the id is : "+ id);
    if(id){
      this.leaveService.getLeaveRequestsById(+id).subscribe({
        next:(value) =>{
            this.leaveRequest=value ;
            console.log("came");
        },
        error:(error)=>{
          console.error("error finding leaves : ", error);
        }


      })
    }
    
  }




}
