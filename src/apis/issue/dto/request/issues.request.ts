import {Field, InputType, Int} from "@nestjs/graphql";
import {IssueType} from "@/apis/integration/type/enum";

@InputType()
export class IssuesRequest {
  @Field(() => IssueType, { nullable: true })
  type?: IssueType;

  @Field(() => Int, )
  countOfItems!: number;

  @Field(() => String, { nullable: true })
  lastItemId?: string;

  @Field(() => String, { nullable: true })
  keyword?: string;
}