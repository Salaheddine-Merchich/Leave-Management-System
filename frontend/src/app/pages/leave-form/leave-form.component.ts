import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { LeaveService, LeaveRequest } from '../../services/leave.service';
import { catchError, of } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-leave-form',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  templateUrl: './leave-form.component.html',
  styleUrl: './leave-form.component.css'
})
export class LeaveFormComponent implements OnInit {
  LeaveForm!: FormGroup;
  isEditMode = false;
  LeaveId: number | null = null;
  isLoading = false;
  error: string | null = null;

  statusOptions = [
    { value: 'APPROVED', label: 'APPROVED' },
    { value: 'PENDING', label: 'PENDING' },
    { value: 'REJECTED', label: 'REJECTED' }
  ];

  constructor(
    private fb: FormBuilder,
    private leaveService: LeaveService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Initialiser le formulaire
    this.LeaveForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      reason: ['', Validators.required],
      status: ['PENDING', Validators.required]
    });

    // Vérifier si on est en mode "édition"
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEditMode = true;
        this.LeaveId = +idParam;
        this.fetchLeave(this.LeaveId);
      }
    });
  }

  fetchLeave(id: number) {
    this.isLoading = true;
    this.leaveService.getLeaveRequestsById(id).pipe(
      catchError(err => {
        this.error = 'Échec du chargement du congé.';
        this.isLoading = false;
        return of(null);
      })
    ).subscribe((leave: LeaveRequest | null) => {
      if (leave) {
        this.LeaveForm.patchValue({
          startDate: leave.startDate,
          endDate: leave.endDate,
          reason: leave.reason,
          status: leave.status
        });
      }
      this.isLoading = false;
    });
  }

  onSubmit() {
    if (this.LeaveForm.invalid) return;
    this.isLoading = true;
    const leaveData = this.LeaveForm.value;

    if (this.isEditMode && this.LeaveId) {
      console.log("haya ghtgad");

      this.leaveService.updateLeaveRequest(this.LeaveId, leaveData).subscribe({
        next: () => this.router.navigate(['/Leaves']),
        error: err => {
          this.error = 'Erreur lors de la mise à jour.';
          this.isLoading = false;
        }

      });
      console.log("tbedlo", leaveData.status);

    } else {
      this.leaveService.createLeaveRequests(leaveData).subscribe({
        next: () => this.router.navigate(['/Leaves']),
        error: err => {
          this.error = 'Erreur lors de la création.';
          this.isLoading = false;
        }
      });
    }
  }
}
