
import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../../model/user';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.less']
})
export class SigninComponent implements OnInit {

  @Output()
  userCreated: EventEmitter<User> = new EventEmitter<User>();

  player: FormGroup = this.fb.group({
    pseudo: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
  });

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  submit() {
    this.userCreated.emit(this.player.value);
    this.player.reset();
  }
}
