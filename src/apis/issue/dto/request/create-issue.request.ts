import {Field, InputType} from "@nestjs/graphql";
import {IssueType} from "@/apis/integration/type/enum";

@InputType()
export class CreateIssueRequest {
  @Field(() => String)
  title:string

  @Field(() => String)
  contents:string

  @Field(() => IssueType)
  type:IssueType

  userId: string

  setUserid(userId: string): CreateIssueRequest {
    this.userId = userId

    return this
  }
}