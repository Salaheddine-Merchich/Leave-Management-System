import { Component,OnInit } from '@angular/core';
import { LeaveRequest,LeaveService } from '../../services/leave.service';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leave-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './leave-list.component.html',
  styleUrl: './leave-list.component.css'
})
export class LeaveListComponent {
  
  leaveRequests:LeaveRequest[]=[];
  constructor(private leaveService:LeaveService){}

  ngOnInit():void{
    this.leaveService.getAllLeaveRequests().subscribe({
      next:(value)=> {
        this.leaveRequests=value;
      },
      error:(error)=>{
        console.error('Error fetching tasks:', error);
      }
    })
  }
  deleteLeaveRequest(id:number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cette tâche ?')) {
      this.leaveService.deleteLeaveRequest(id).subscribe(()=>{
        this.leaveRequests=this.leaveRequests.filter(leaveRequest=> leaveRequest.id !== id);
      }
      )
    }

}
}
