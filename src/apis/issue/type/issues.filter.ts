import {IssueType} from "@/apis/integration/type/enum";

export interface IssuesFilter {
  type?: IssueType
  countOfItems: number
  lastItemId?: string
  keyword?: string
}