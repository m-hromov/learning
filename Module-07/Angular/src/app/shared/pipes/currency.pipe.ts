import {Pipe, PipeTransform} from '@angular/core';
const currencies = new Map<string, {multiplier: number, symbol: string}> ([
  ["USD", {multiplier: 1, symbol: "$"}],
  ["UAH", {multiplier: 8, symbol: "UAH"}]
])

@Pipe({
  name: 'currencyCustom'
})
export class CurrencyPipe implements PipeTransform {

  transform(value: number, tag: string, tagPreceding: boolean): string {
    let entry = currencies.get(tag);
    if (entry) {
      return `${tagPreceding ? entry.symbol : ''}${(value * entry.multiplier).toString()}${!tagPreceding ? entry.symbol : ''}`;
    }
    return value.toString()
  }

}
