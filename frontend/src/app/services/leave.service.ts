import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService,User } from './user.service';


export interface LeaveRequest{
  id:number;
  startDate:Date;
  endDate:Date;
  reason:string;
  status:string;
}

@Injectable({
  providedIn: 'root'
})
export class LeaveService {
    private apiUrl = 'http://localhost:8081/api/leave-requests';

    constructor(private http:HttpClient) { }

    getAllLeaveRequests(): Observable<LeaveRequest[]> {
        return this.http.get<LeaveRequest[]>('http://localhost:8081/api/leave-requests/all');
    }
    getLeaveRequestsById(id: number): Observable<LeaveRequest> {
      return this.http.get<LeaveRequest>(`${this.apiUrl}/${id}`);
    }
    createLeaveRequests(leaveRequest: LeaveRequest): Observable<LeaveRequest> {
      return this.http.post<LeaveRequest>(this.apiUrl, leaveRequest);
    } 
    
    updateLeaveRequest(id: number, leaveRequest: LeaveRequest): Observable<LeaveRequest> {
      return this.http.put<LeaveRequest>(`${this.apiUrl}/${id}`, leaveRequest);
    }
    deleteLeaveRequest(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }



  }
