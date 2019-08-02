import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-schedule-time-edit',
  templateUrl: './schedule-time-edit.component.html'
})
export class ScheduleTimeEditComponent implements OnInit {
  cronExpression = '0 0 0 0 0 0'; //cron表达式和初始值

  // @Input() cronDesc: string

  @Output() cronExpOut = new EventEmitter()

  @Output() cronRemarkOut = new EventEmitter()

  //下拉框选择
  seconds: any[] = [];
  minutes: any[] = [];
  hours: any[] = [];
  days: any[] = [];
  mouths: any[] = [];

  remark: string;

  constructor() { }

  ngOnInit() {
    // this.desc = this.cronDesc
    //初始化下拉框展示数据
    for (let i = 0; i < 60; i++) {
      this.minutes.push({ label: i + '', value: i + '' });
      this.seconds.push({ label: i + '', value: i + '' });
    }
    for (let i = 0; i < 24; i++) {
      this.hours.push({ label: i + '', value: i + '' });
    }
    for (let i = 0; i < 32; i++) {
      this.days.push({ label: i + '', value: i + '' });
    }
    for (let i = 0; i < 13; i++) {
      this.mouths.push({ label: i + '', value: i + '' });
    }
  }



  index: number = 0 //当前选择位置

  //下拉框绑定值
  Year: string = '0'
  DayOfWeek: string[] = [];
  Months: string = '0'
  FirstMonth: string = '0'
  DayofMonth: string = '0'
  Hours: string = '0'
  Minutes: string = '0'
  Seconds: string = '0'

  //当前位置改变
  onTabChange(event) {
    this.index = event.index
    this.cronExpression = '0 0 0 0 0 0'
    this.Year = '0'
    this.DayOfWeek = [];
    this.Months = '0'
    this.FirstMonth = '0'
    this.DayofMonth = '0'
    this.Hours = '0'
    this.Minutes = '0'
    this.Seconds = '0'
  }

  mouthChange() {
    let splits: string[] = this.cronExpression.split(' ')
    if (this.index == 5) {
      splits[4] = this.FirstMonth + '/' + this.Months
      splits[5] = '?'
    } else {
      splits[4] = this.DayofMonth
    }
    this.changeCronExpression(splits)
  }

  weekChange() {
    let splits: string[] = this.cronExpression.split(' ')
    //若果当前位置在星期选择
    if (this.index == 4) {
      let dw = ''
      //如果没有选星期，则默认为星期天
      if (this.DayOfWeek.length == 0) {
        dw += 'SUN'
      }
      //遍历选择的星期，拼接到表达式
      for (let index = 0; index < this.DayOfWeek.length; index++) {
        if ((index + 1) == this.DayOfWeek.length) {
          //最后一个位置不加逗号
          dw += this.DayOfWeek[index]
        } else {
          dw += this.DayOfWeek[index] + ','
        }
      }
      splits[5] = dw
      splits[4] = '*'
      splits[3] = '?'
    } else {
      splits[5] = this.DayofMonth[0]
      splits[3] = '?'
    }
    this.changeCronExpression(splits)
  }

  dayChange() {
    let splits: string[] = this.cronExpression.split(' ')
    if (this.index == 3) {//如果当前位置在日期选择位置
      splits[3] = '1/' + this.DayofMonth
      splits[4] = '*'
      splits[5] = '?'
    } else if (this.index == 4) {
      splits[3] = this.DayofMonth
    }
    this.changeCronExpression(splits)
  }

  hourChange() {
    let splits: string[] = this.cronExpression.split(' ')
    if (this.index == 2) {
      splits[2] = '0/' + this.Hours
      splits[3] = '*'
      splits[4] = '*'
      splits[5] = '?'
    } else {
      splits[2] = this.Hours
    }
    this.changeCronExpression(splits)
  }
  minuteChange() {
    let splits: string[] = this.cronExpression.split(' ')
    if (this.index == 1) {
      splits[1] = '0/' + this.Minutes
      splits[2] = '*'
      splits[3] = '*'
      splits[4] = '*'
      splits[5] = '?'
    } else {
      splits[1] = this.Minutes
    }
    this.changeCronExpression(splits)
  }

  //秒改变
  secondChange() {
    let splits: string[] = this.cronExpression.split(' ')
    if (this.index == 0) {
      splits[0] = '0/' + this.Seconds
      splits[1] = '*'
      splits[2] = '*'
      splits[3] = '*'
      splits[4] = '*'
      splits[5] = '?'
    } else {
      splits[0] = this.Seconds
    }
    this.changeCronExpression(splits)
  }

  //修改cron表达式
  changeCronExpression(splits: string[]) {
    let cronE = ''
    for (let index = 0; index < splits.length; index++) {
      if (index == 0) {
        cronE += splits[index]
      } else {
        cronE += ' ' + splits[index]
      }
    }
    this.cronExpression = cronE
    this.formatDesc()
    this.cronExpOut.emit(this.cronExpression)
    this.cronRemarkOut.emit(this.remark)
  }

  //组装desc
  formatDesc() {
    if (this.index == 4) {
      this.remark = "每到" + this.DayOfWeek + "，在" + this.Hours + '点' + this.Minutes + '分' + this.Seconds + '秒，执行一次'
    } else if (this.index == 5) {
      this.remark = "每隔" + this.Months + "月，在" + this.DayofMonth + '日' + this.Hours + '点' + this.Minutes + '分' + this.Seconds + '秒，执行一次'
    } else if (this.index == 3) {
      this.remark = "每隔" + this.DayofMonth + '天，在' + this.Hours + '点' + this.Minutes + '分' + this.Seconds + '秒，执行一次'
    } else if (this.index == 2) {
      this.remark = "每隔" + this.Hours + '小时，在' + this.Minutes + '分' + this.Seconds + '秒，执行一次'
    } else if (this.index == 1) {
      this.remark = "每隔" + this.Minutes + '分钟，在' + this.Seconds + '秒，执行一次'
    } else if (this.index == 0) {
      this.remark = "每隔" + this.Seconds + '秒，执行一次'
    }
  }
}
