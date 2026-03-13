import { Routes } from '@angular/router';

export const routes: Routes = [
    { path : '', loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)},
    { path : 'Leaves', loadComponent: () => import('./pages/leave-list/leave-list.component').then(m => m.LeaveListComponent)},
    { path : 'info-leave/:id', loadComponent: () => import('./pages/leave-info/leave-info.component').then(m => m.LeaveInfoComponent)},
    { path : 'edit-leave/:id', loadComponent: () => import('./pages/leave-form/leave-form.component').then(m => m.LeaveFormComponent)},
    { path : 'add-leave', loadComponent: () => import('./pages/leave-form/leave-form.component').then(m => m.LeaveFormComponent)},
    { path : 'employees', loadComponent: () => import('./pages/employee-list/employee-list.component').then(m => m.EmployeeListComponent)},
    { path : 'info-employee/:id', loadComponent: () => import('./pages/employee-info/employee-info.component').then(m => m.EmployeeInfoComponent)},
];
