import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AddTransactionComponent} from './add-transaction/add-transaction.component';
import {ErrorPageComponent} from './error-page/error-page.component';
import {RemoveTransactionComponent} from './remove-transaction/remove-transaction.component';
import {AuthGuard} from "./auth.guard";
import {LoginComponent} from "./login/login.component";
import {OutgoingTransactionsComponent} from "./outgoing-transactions/outgoing-transactions.component";
import {IncomingTransactionsComponent} from "./incoming-transactions/incoming-transactions.component";

export const routes: Routes = [
  {path: '', component: HomeComponent, data: {title: 'Home - Payment Transactions App'}},
  { path: 'incoming-transactions', component: IncomingTransactionsComponent, data: { title: 'Incoming Transactions' }, canActivate: [AuthGuard] },
  { path: 'outgoing-transactions', component: OutgoingTransactionsComponent, data: { title: 'Outgoing Transactions' }, canActivate: [AuthGuard] },
  {path: 'login', component: LoginComponent, data: {title: 'Login'}},
  {path: 'add-transaction', component: AddTransactionComponent, data: {title: 'Add Transaction'}, canActivate: [AuthGuard]},
  {path: 'remove-transaction', component: RemoveTransactionComponent, data: {title: 'Remove Transaction'}, canActivate: [AuthGuard]},
  {path: '**', data: {title: 'Error 404'}, component: ErrorPageComponent},
];
