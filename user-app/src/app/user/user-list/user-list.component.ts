import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers: [UserService]
})
export class UserListComponent implements OnInit {

  public users: User[];

  constructor(private router: Router,
    private userService: UserService) { }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.findAll().subscribe(
     users => { this.users = users; },
     err => { console.log(err); }
    );
  }

  redirectNewUserPage() {
    this.router.navigate(['/user/create']);
  }

  editUserPage(user: User) {
    console.log('Edit User');
    //if (user) {
    //  this.router.navigate(['/user/edit', user.userId]);
    //}
  }

  deleteUser(user: User) {
    console.log('Delete User');
  }


}
