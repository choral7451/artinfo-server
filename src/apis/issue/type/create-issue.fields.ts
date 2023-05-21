import {IssueType} from "@/apis/integration/type/enum";

export interface CreateIssueFields {
  userId: string
  type: IssueType
  title: string
  contents: string
}