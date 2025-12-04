import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component'
import { LeaveListComponent } from './pages/leave-list/leave-list.component'
import { LeaveInfoComponent } from './pages/leave-info/leave-info.component'
import { LeaveFormComponent } from './pages/leave-form/leave-form.component'
import { EmployeeListComponent } from './pages/employee-list/employee-list.component'
import { EmployeeInfoComponent } from './pages/employee-info/employee-info.component'

export const routes: Routes = [
    { path : '', component: HomeComponent},
    { path : 'Leaves', component: LeaveListComponent},
    { path : 'info-leave/:id', component: LeaveInfoComponent},
    { path : 'edit-leave/:id', component: LeaveFormComponent},
    { path : 'add-leave', component: LeaveFormComponent},
    { path : 'employees', component: EmployeeListComponent},
    { path : 'info-employee/:id', component: EmployeeInfoComponent},

];
