import {IssueType} from "@/apis/integration/type/enum";

export interface CountIssuesFilter {
  type?: IssueType
  keyword?: string
}