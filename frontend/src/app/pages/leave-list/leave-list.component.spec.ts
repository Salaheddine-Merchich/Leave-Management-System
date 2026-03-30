import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { LeaveListComponent } from './leave-list.component';
import { LeaveService, LeaveRequest } from '../../services/leave.service';

describe('LeaveListComponent', () => {
  let component: LeaveListComponent;
  let fixture: ComponentFixture<LeaveListComponent>;
  let leaveServiceMock: any;

  beforeEach(async () => {
    leaveServiceMock = jasmine.createSpyObj('LeaveService', ['getAllLeaveRequests', 'deleteLeaveRequest']);
    
    const mockLeaves: LeaveRequest[] = [
      { id: 1, reason: 'Vacances', startDate: new Date(), endDate: new Date(), status: 'PENDING' },
      { id: 2, reason: 'Maladie', startDate: new Date(), endDate: new Date(), status: 'APPROVED' }
    ];
    leaveServiceMock.getAllLeaveRequests.and.returnValue(of(mockLeaves));
    leaveServiceMock.deleteLeaveRequest.and.returnValue(of(null));

    await TestBed.configureTestingModule({
      imports: [LeaveListComponent],
      providers: [
        provideRouter([]),
        { provide: LeaveService, useValue: leaveServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LeaveListComponent);
    component = fixture.componentInstance;
  });

  it('devrait récupérer la liste des congés à l\'initialisation', () => {
    fixture.detectChanges(); // Trigger ngOnInit
    expect(leaveServiceMock.getAllLeaveRequests).toHaveBeenCalled();
    expect(component.leaveRequests.length).toBe(2);
    expect(component.leaveRequests[0].id).toBe(1);
  });

  it('devrait appeler le service de suppression et retirer le congé de la liste', () => {
    fixture.detectChanges(); // initial load
    
    spyOn(window, 'confirm').and.returnValue(true);
    
    component.deleteLeaveRequest(1);
    
    expect(window.confirm).toHaveBeenCalledWith('Êtes-vous sûr de vouloir supprimer cette tâche ?');
    expect(leaveServiceMock.deleteLeaveRequest).toHaveBeenCalledWith(1);
    
    expect(component.leaveRequests.length).toBe(1);
    expect(component.leaveRequests[0].id).toBe(2);
  });
});
