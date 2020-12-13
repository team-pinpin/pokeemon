import { Component, OnInit } from '@angular/core';
// @ts-ignore
import itemsJson from '../../../../../Assets/items.json';
import { Observable, Subscription } from 'rxjs';
import { Item } from 'src/model/item';
import { UserItemService } from 'src/app/services/user-item-service';
import { UserApiService } from 'src/app/services/user-api.service';
import { User } from 'src/app/model/user';
import { UserItem } from 'src/app/model/user-item';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.less']
})
export class ShopComponent implements OnInit {

  items: Item[] = itemsJson;
  userItem: UserItem = null;
  user: Observable<User>;
  id: number = 1;

  private _subscription: Subscription[] = [];

  quantity: number = 1;
  coins: number = 0;
  isHidden: boolean = true;

  constructor(private userItemApi: UserItemService, private userApi: UserApiService) {
  }

  ngOnInit(): void {
    this.userApi.getById(this.id)
      .subscribe(user => {
        this.coins = user.money;
      }
      );
  }

  modifyCoins(event) {
    console.log("Coins spent : ", event);
    var diff = this.coins - event;
    if (diff < 0) {
      console.log("No money for this");
      this.isHidden = false;
      return;
    } else {
      this.coins -= event;
      this.userApi.getById(this.id)
        .subscribe(user => {
          console.log("Avant update : " + user.money);
          //user.money = this.coins;
          let userEncode = {
            pseudo: user.pseudo,
            email: user.email,
            password: user.password,
            money: this.coins
          };
          this.userApi.update(this.id, userEncode);
          console.log("Après update : " + userEncode.money);
        }
        );
      this.isHidden = true;
    }
  }

  ngOnDestroy() {
    this._subscription.forEach(sub => sub && sub.unsubscribe());
  }
}