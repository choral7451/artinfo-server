import {IssueType} from "@/apis/integration/enum/enum";

export interface CreateIssueFields {
  userId: string
  type: IssueType
  title: string
  contents: string
}